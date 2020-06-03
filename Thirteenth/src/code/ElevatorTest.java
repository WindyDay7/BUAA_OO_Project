package code;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElevatorTest {

	Elevator Test_Elevator = new Elevator();

	@Test
	public void Test_change() {
		this.Test_Elevator.state_in[0] = 1;
		this.Test_Elevator.move[0] = 7;
		this.Test_Elevator.st_time = 1;
		long[] a1={6,7,1,1,0};
		long[] a2={6,7,2,0,0};
		long[] a3={7,15,2,0,0}; 
		long[] a4={5,15,1,1,0}; 
		this.Test_Elevator.in_way[6][0] = a1;
		this.Test_Elevator.in_way[6][1] = a2; 
		this.Test_Elevator.in_way[7][0] = a3; 
		this.Test_Elevator.in_way[8][0] = a4; 
		this.Test_Elevator.change();
	}

	@Test
	public void Test_change_1() {
		this.Test_Elevator.state_in[0] = 1;
		this.Test_Elevator.move[0] = 7;
		this.Test_Elevator.st_time = 1;
		long[] a1={6,2,1,1,0};
		long[] a2={6,2,2,0,0};
		long[] a3={7,3,2,0,0}; 
		long[] a4={5,3,1,1,0}; 
		long[] a5={8,3,1,1,0}; 
		this.Test_Elevator.in_way[6][0] = a1;
		this.Test_Elevator.in_way[6][1] = a2; 
		this.Test_Elevator.in_way[7][0] = a3; 
		this.Test_Elevator.in_way[5][0] = a4; 
		this.Test_Elevator.in_way[8][0] = a5; 

		this.Test_Elevator.change();
	}

	
    @Test
    public void Test_change2() {
        this.Test_Elevator.state_in[0] = 10;
        this.Test_Elevator.move[0] = 2;
        this.Test_Elevator.st_time = 1;
        long[] a1={3,7,1,2,0};
        long[] a2={3,7,2,0,0};
        long[] a3={2,15,2,0,0}; 
        long[] a4={5,5,1,2,0}; 
        this.Test_Elevator.in_way[3][1] = a1;
        this.Test_Elevator.in_way[3][0] = a2; 
        this.Test_Elevator.in_way[2][0] = a3; 
        this.Test_Elevator.in_way[5][0] = a4; 
        this.Test_Elevator.change();
    }

	@Test
	public void Test_change_2() {
		this.Test_Elevator.state_in[0] = 10;
		this.Test_Elevator.move[0] = 2;
		this.Test_Elevator.st_time = 1;
		long[] a1={3,2,1,2,0};
		long[] a2={3,2,2,0,0};
		long[] a3={2,5,2,0,0}; 
		long[] a4={5,5,1,2,0}; 
		this.Test_Elevator.in_way[3][1] = a1;
		this.Test_Elevator.in_way[3][0] = a2; 
		this.Test_Elevator.in_way[2][0] = a3; 
		this.Test_Elevator.in_way[5][0] = a4; 
		this.Test_Elevator.change();
	}

	@Test
	public void Test_change3() {
		this.Test_Elevator.state_in[0] = 5;
		this.Test_Elevator.move[0] = 5;
		this.Test_Elevator.st_time = 2;
		long[] a1={5,7,1,2,0};
		this.Test_Elevator.in_way[5][0] = a1; 
		this.Test_Elevator.change();
	}

	@Test
	public void Test_change_3() {
		this.Test_Elevator.state_in[0] = 5;
		this.Test_Elevator.move[0] = 5;
		this.Test_Elevator.st_time = 12;
		long[] a1={5,7,1,2,0};
		this.Test_Elevator.in_way[5][0] = a1; 
		this.Test_Elevator.change();
	}


	@Test
	public void Test_toString() {
		this.Test_Elevator.state_in[1] = 1;
		this.Test_Elevator.toString();
		this.Test_Elevator.state_in[1] = 2;
		this.Test_Elevator.toString();
		this.Test_Elevator.state_in[1] = 3;
		this.Test_Elevator.toString();
		this.Test_Elevator.state_in[1] = 4;
		this.Test_Elevator.toString();
	}

	@Test
	public void Test_repOK() {
		this.Test_Elevator.state_in[0] = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.state_in[0] = 1;
		this.Test_Elevator.move[0] = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.move[0] = 1;
		this.Test_Elevator.empty[0] = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.empty[0] = 1;
		this.Test_Elevator.button[0][0] = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.button[0][0] = 1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.st_time = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.st_time = 1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.mo_time = -1;
		this.Test_Elevator.repOK();
		this.Test_Elevator.st_time = 1;
		this.Test_Elevator.repOK();
	}

}
