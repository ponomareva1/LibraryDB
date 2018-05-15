package com.utils;

import com.controller.ScreensController;

public interface ControlledScreen {
    //This method will allow the injection of the Parent ScreenPane
    void setScreenParent(ScreensController screenPage);
}
