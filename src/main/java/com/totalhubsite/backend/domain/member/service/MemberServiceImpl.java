package com.totalhubsite.backend.domain.member.service;

import com.totalhubsite.backend.domain.member.dto.request.SignUpRequestDto;
import com.totalhubsite.backend.domain.member.dto.response.SingUpResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.domain.member.exception.UserAlreadyRegisteredException;
import com.totalhubsite.backend.domain.member.repository.MemberRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SingUpResponseDto signup(SignUpRequestDto requestDto) {

        memberRepository.findByEmail(requestDto.email())
            .ifPresent(user -> {
                throw new UserAlreadyRegisteredException();
            });

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        Member member = requestDto.toEntity(encodedPassword, generateNickname());
        memberRepository.save(member);

        SingUpResponseDto responseDto = SingUpResponseDto.fromEntity(member);

        return responseDto;
    }

    public String generateNickname() {
        List<String> first = Arrays.asList("자유로운", "서운한",
            "당당한", "배부른", "수줍은", "멋있는",
            "용기있는", "심심한", "잘생긴", "이쁜", "눈웃음치는", "행복한", "사랑스러운", "순수한");
        List<String> name = Arrays.asList("사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리",
            "참새", "고슴도치", "강아지", "고양이", "거북이", "토끼", "앵무새", "하이에나", "펭귄", "하마",
            "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐", "판다", "코알라", "앵무새", "독수리", "알파카");
        Collections.shuffle(first);
        Collections.shuffle(name);
        return first.get(0) + name.get(0);
    }

}
