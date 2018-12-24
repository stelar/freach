package maint;

import java.util.Random;

public class sam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		String id = String.format("%04d", rand.nextInt(10000));
		System.out.println(id);
	}

}
