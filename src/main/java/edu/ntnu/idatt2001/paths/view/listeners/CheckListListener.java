package edu.ntnu.idatt2001.paths.view.listeners;


import java.util.List;

/**
 * Interface for setting actions to the buttons of a base frame.
 *
 * @author Tobias Oftedal
 * @author Ramtin Samavat
 * @version 1.0
 * @since May 16, 2023.
 */
public interface CheckListListener {

  void onConfirmSelectionsClicked(List<String> selectedGoals);

}

