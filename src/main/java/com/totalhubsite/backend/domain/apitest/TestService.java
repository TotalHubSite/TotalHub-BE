package com.totalhubsite.backend.domain.apitest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public TestResponseDto addData(TestRequestDto requestDto) {

        TestEntity testEntity = TestEntity.builder()
            .content(requestDto.content())
            .build();

        TestEntity savedEntity = testRepository.save(testEntity);

        TestResponseDto responseDto = TestResponseDto.builder()
            .id(savedEntity.getId())
            .content(savedEntity.getContent())
            .build();

        System.out.println(responseDto);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public TestResponseDto findData(Long testId) {

        TestEntity findEntity = testRepository.findById(testId).orElseThrow();

        TestResponseDto responseDto = TestResponseDto.builder()
            .id(findEntity.getId())
            .content(findEntity.getContent())
            .build();

        System.out.println(responseDto);

        return responseDto;
    }
}
