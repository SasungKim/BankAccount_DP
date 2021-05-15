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
public class Silver extends Level {
    private final double fee = 20;
    private final String level = "Sliver";

    @Override
    public void Change(Customer c){
        double balance = c.getBalance();
        if (balance<10000)
            c.setLevel(new Silver());
        else if(balance >= 10000 && balance < 20000)
            c.setLevel(new Gold());
        else
            c.setLevel(new Platinum());
    }
    
    @Override
    public double getFee(){
        return fee;
    }
    
    @Override
    public String toString(){
        return level;
    }
}
