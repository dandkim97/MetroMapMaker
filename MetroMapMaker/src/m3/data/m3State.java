/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

/**
 *
 * @author Dan Kim
 */
public enum m3State {
    SELECTING_SHAPE,
    DRAGGING_SHAPE,
    STARTING_RECTANGLE,
    STARTING_ELLIPSE,
    STARTING_TEXT,
    STARTING_IMAGE,
    SIZING_SHAPE,
    DRAGGING_NOTHING,
    SIZING_NOTHING
}
