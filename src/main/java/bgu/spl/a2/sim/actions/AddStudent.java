package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.Collection;

public class AddStudent extends Action<String> {

    private String name;

    public AddStudent(String name){
        super();
        this.name = name;

        setActionName("Add Student");
    }

    @Override
    protected void start() {
        Action<String> newStu = new Action<String>() {
            @Override
            protected void start() {
                setActionName("New student");
                complete("Student created");
            }
        };
        Collection<Action<String>> actions = new ArrayList<>();
        actions.add(newStu);
        sendMessage(newStu, name, new StudentPrivateState()); //send a message to the studenet actor to create a new student
        then(actions, new callback() {
            @Override
            public void call() {
                DepartmentPrivateState department = (DepartmentPrivateState) pool.getPrivateState(actorID);
                department.addStudent(name);
                complete("Student added to department");
            }
        });
    }

}
