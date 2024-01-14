package com.totalhubsite.backend.apitest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping
    public TestResponseDto dataAdd(
        @RequestBody TestRequestDto requestDto
    ) {

        TestResponseDto responseDto = testService.addData(requestDto);

        return responseDto;
    }

    @GetMapping("{testId}")
    public TestResponseDto dataDetails(
        @PathVariable Long testId
    ) {

        TestResponseDto responseDto = testService.findData(testId);

        return responseDto;
    }

}
