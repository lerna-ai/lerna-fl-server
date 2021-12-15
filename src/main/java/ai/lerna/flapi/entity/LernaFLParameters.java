/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai.lerna.flapi.entity;

/**
 *
 * @author gkellaris
 */
public class LernaFLParameters {
    private int minNoUsers; //Minimum amount of users to start FL training
    private int noJobs;   //Number of models for 1vAll Logistic Regression
    
    public int getMinNoUsers() {
        return minNoUsers;
    }
    
    public void setMinNoUsers(int minNoUsers) {
        this.minNoUsers = minNoUsers;
    }
    
    public int getNoJobs() {
        return noJobs;
    }
    
    public void setNoJobs(int noJobs) {
        this.noJobs = noJobs;
    }
}
