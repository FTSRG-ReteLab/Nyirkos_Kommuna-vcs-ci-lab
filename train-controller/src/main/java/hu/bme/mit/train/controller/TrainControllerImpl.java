package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;

import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;

	//the speed limit of the train
	private int speedLimit = 0;

	private int time =0;
	private Timer timer = new Timer();
	private Table<Integer, Integer, Integer> tachoGraph= HashBasedTable.create();

	public void startTrainController(int t){
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				followSpeed();
			}
		}, t);
	}


	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();

		tachoGraph.put(time, step, referenceSpeed);
		time++;
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public Table getTachoGraph(){
		return tachoGraph;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}


	//branch A change
	//this
	//is
	//not
	//the
	//same
	//comment
	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
	}

}
