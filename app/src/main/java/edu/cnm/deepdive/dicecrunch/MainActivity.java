package edu.cnm.deepdive.dicecrunch;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.dicecrunch.databinding.ActivityMainBinding;
import edu.cnm.deepdive.dicecrunch.viewmodel.CalculatorViewModel;

public class MainActivity extends AppCompatActivity {

  private CalculatorViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
    binding.setViewModel(viewModel);
    binding.setLifecycleOwner(this);
  }
}