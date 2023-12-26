package com.projectBackend.project.service;

import com.projectBackend.project.dto.PerformerDto;
import com.projectBackend.project.dto.TicketerDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Performance;
import com.projectBackend.project.entity.Performer;
import com.projectBackend.project.entity.Ticketer;
import com.projectBackend.project.repository.PerformanceRepository;
import com.projectBackend.project.repository.TicketerRepository;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketerService {
    private final TicketerRepository ticketerRepository;
    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;

    //티켓터 조회
    public List<TicketerDto> getTicketerList() {
        List<Ticketer> ticketerList = ticketerRepository.findAll();
        List<TicketerDto> ticketerDtoList = new ArrayList<>();
        System.out.println("서비스 getTicketerList");

        for (Ticketer ticketer : ticketerList) {
            ticketerDtoList.add(convertEntityToDto(ticketer));
        }
        return ticketerDtoList;


    }

    // 특정 공연 티켓터 조회
    public List<TicketerDto> getTicketerListByPerformanceId(Long performanceId) {
        List<Ticketer> ticketerList = ticketerRepository.findByPerformance_PerformanceId(performanceId);
        List<TicketerDto> ticketerDtoList = new ArrayList<>();

        for (Ticketer ticketer : ticketerList) {
            ticketerDtoList.add(convertEntityToDto(ticketer));
        }
        return ticketerDtoList;
    }


    // 티켓터 등록
    public boolean saveTicketer(TicketerDto ticketerDto) {
        Member member = userRepository.findById(ticketerDto.getUserId()) // member에서 userId로 찾기
                .orElseThrow(() -> new RuntimeException("잘못된 유저ID:" + ticketerDto.getUserId())); // 없으면 예외처리

        if (member.getUserPoint() - ticketerDto.getPrice() < 0) { // member에서 찾은 userPoint가 price보다 작으면 false리턴
            return false;
        }

        member.setUserPoint(member.getUserPoint() - ticketerDto.getPrice()); // member에서 찾은 userPoint에서 price를 뺀 값을 다시 userPoint에 저장
        userRepository.save(member);

        Performance performance = performanceRepository.findById(ticketerDto.getPerformanceId()) // performance에서 performanceId로 찾기
                .orElseThrow(() -> new RuntimeException("잘못된 공연ID:" + ticketerDto.getPerformanceId())); // 없으면 예외처리

        Ticketer ticketer = new Ticketer();
        ticketer.setPerformance(performance);
        ticketer.setMember(member);
        ticketerRepository.save(ticketer);

        return true;
    }

    private TicketerDto convertEntityToDto(Ticketer ticketer) {
        TicketerDto ticketerDto = new TicketerDto();
        ticketerDto.setTicketerId(ticketer.getTicketerId());
        ticketerDto.setPerformanceId(Long.valueOf(ticketer.getPerformance().getPerformanceId().toString()));
        ticketerDto.setUserId(ticketer.getMember().getId());
        return ticketerDto;
    }
}
    // 공연자 등록
//    public boolean saveTicketer(TicketerDto ticketerDto) {
//        try {
//            Ticketer ticketer = new Ticketer();
//        }
//        return true;
//    }


