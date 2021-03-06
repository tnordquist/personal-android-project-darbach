package edu.cnm.deepdive.dicecrunch.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.dicecrunch.model.entity.Face;
import edu.cnm.deepdive.dicecrunch.model.pojo.DieWithFaces;
import edu.cnm.deepdive.dicecrunch.service.DieRepository;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CustomDieViewModel extends AndroidViewModel implements LifecycleObserver {

  public final MutableLiveData<String> dieName;
  public final MutableLiveData<String> calculatorSymbol;
  public final MutableLiveData<String> face1;
  public final MutableLiveData<String> face2;
  public final MutableLiveData<String> face3;
  public final MutableLiveData<String> face4;
  public final MutableLiveData<String> face5;
  public final MutableLiveData<String> face6;
  private final List<MutableLiveData<String>> faces;
  private final Random rng;
  private final DieRepository dieRepository;
  private final MutableLiveData<Throwable> throwable;

  public CustomDieViewModel(@NonNull Application application) {
    super(application);
    dieName = new MutableLiveData<>("Hero Quest combat die");
    calculatorSymbol = new MutableLiveData<>("");
    face1 = new MutableLiveData<>("");
    face2 = new MutableLiveData<>("");
    face3 = new MutableLiveData<>("");
    face4 = new MutableLiveData<>("");
    face5 = new MutableLiveData<>("");
    face6 = new MutableLiveData<>("");
    faces = new LinkedList<>();
    Collections.addAll(faces, face1, face2, face3, face4, face5, face6);
    rng = new SecureRandom();
    throwable = new MutableLiveData<>();
    dieRepository = new DieRepository(application);
  }

  public MutableLiveData<String> getDieName() {
    return dieName;
  }

  public MutableLiveData<String> getCalculatorSymbol() {
    return calculatorSymbol;
  }

  public MutableLiveData<String> getFace1() {
    return face1;
  }

  public MutableLiveData<String> getFace2() {
    return face2;
  }

  public MutableLiveData<String> getFace3() {
    return face3;
  }

  public MutableLiveData<String> getFace4() {
    return face4;
  }

  public MutableLiveData<String> getFace5() {
    return face5;
  }

  public MutableLiveData<String> getFace6() {
    return face6;
  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void saveDie(View view) {
    DieWithFaces die = new DieWithFaces();
    List<Face> faces = die.getFaces();
    for (MutableLiveData<String> faceEntry : this.faces) {
      Face face = new Face();
      face.setDie(die);
      face.setName(faceEntry.getValue());
      faces.add(face);
    }
    die.setName(dieName.getValue());
    die.setFaces(faces);
    dieRepository
        .save(die)
        .subscribe(
            (d) -> { /* TODO Notify user of success. */ },
            this::postThrowable
        );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

  public void rollDice() {
    int dieThrow = rng.nextInt(6);

  }
}
