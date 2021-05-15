/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

/**
 *
 * @author Sasung Kim
 */

//State Pattern
public abstract class Level {
    public abstract void Change(Customer c);
    public abstract double getFee();
}
