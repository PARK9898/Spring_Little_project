package com.group.libraryapp.dto.calulator.request;
// 정보를 전달하는 객체의 이름을 DTO ( Data Transfer Object)
public class CalculatorAddRequest {
	public CalculatorAddRequest(int number1, int number2) {
		this.number1 = number1;
		this.number2 = number2;
	}

	public int getNumber1() {
		return number1;
	}

	public int getNumber2() {
		return number2;
	}

	private final int number1;
	private final int number2;

}
