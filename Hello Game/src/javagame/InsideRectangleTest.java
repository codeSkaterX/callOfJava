package javagame;

import static org.junit.Assert.*;

import org.junit.Test;

public class InsideRectangleTest {

	@Test
	public void testOutsideRectBottomRightFar() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 4, 4));
	}

	@Test
	public void testTopLeftCorner() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 1, 1)); 
	}
	
	@Test
	public void testBottomLeftCornerFar() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 1, 3));
	}
	
	@Test
	public void testBottomRightCorner() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 2, 2));
	}
	
	@Test
	public void testTopRightCorner() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 2, 1));
	}
	
	@Test
	public void testBottomRightCornerTooMuchBoth() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 3, 4));
	}
	
	@Test
	public void testBottomRightCornerTooMuchY() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 2, 3));
	}
	
	@Test
	public void testBottomRightTooLarge() {
		assertFalse(Menu.insideRectangle(1, 1, 2, 2, 230, 230));
	}

}
