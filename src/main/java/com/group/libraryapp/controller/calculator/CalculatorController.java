package com.group.libraryapp.controller.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.calulator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calulator.request.CalculatorMultiplyRequest;

//이 클래스를 진입 지점으로 만들어줌
//POST는 GET과 다르게 BODY에 저장한다 GET은 쿼리에 저장
@RestController //(API 입구)
public class CalculatorController {
	//스프링 부트에서 객체에 값을 집어넣어서 컨트롤러에 넘겨준다
	@GetMapping("/add") // 쿼리를 통해서 넘어온 인자들을 함수에 연결해줄때는  RequestParam 작성해줘야함 같은 이름을 가진 쿼리의 값이 들어온다
	public int addTwoNumbers(CalculatorAddRequest request) {
		return request.getNumber1() + request.getNumber2();
	}
	// POST 에서 @RequestBody가 있어야 정상적으로 바디안에 담긴 JSON을 밑의 객체로 변환시킬 수 있다
	@PostMapping("/multiply")
	public int multiflyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
		return  request.getNumber1() + request.getNumber2();
	}

	//HTTP Method -> GET
	//HTTP PATH -> /add
	//쿼리(key&value0 -> int1, int2
	//API 반환결과 - 덧셈 결과
}
