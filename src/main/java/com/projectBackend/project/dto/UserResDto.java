package com.projectBackend.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.projectBackend.project.constant.Authority;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Performance;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// v 장현준 추가, null 값이 있는 필드를 JSON 응답에서 제외시킴, 일단 막음
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResDto {
    private String userEmail;
    private String userPasswword;
    private String userNickname;
    private String userName;
    private String userAddr;
    private String userPhone;
    private String userGen;
    private int userAge;
    private int userPoint;
    private String BUSINESS_NUM;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // 장현준 이메일로 공연조회용
    private List<PerformanceDto> performances;
//    private List<String> nicknames;
    public void setPerformances(List<PerformanceDto> performances) {
        this.performances = performances;
    }

    // Member -> MemberResDto
    public static UserResDto of(Member member) {
        // 응답에는 비밀번호가 필요없기에 일다 제외
        return UserResDto.builder()
                .userEmail(member.getUserEmail())
                .userNickname(member.getUserNickname())
                .userName(member.getUserName())
                .userAddr(member.getUserAddr())
                .userPhone(member.getUserPhone())
                .userGen(member.getUserGen())
                .userAge(member.getUserAge())
                .userPoint(member.getUserPoint())
                .BUSINESS_NUM(member.getBUSINESS_NUM())
                .authority(member.getAuthority())
                .build();
    }
}
