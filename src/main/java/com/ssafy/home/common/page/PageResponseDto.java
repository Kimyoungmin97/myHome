package com.ssafy.home.common.page;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Data
public class PageResponseDto<E> {
    private List<E> dtoList;// 현재 페이지에 해당하는 데이터 목록
    private List<Integer> pageNumList;  // 페이지 번호 리스트 (ex. [1, 2, 3, ..., 10])
    private PageRequestDto pageRequestDTO;   // 요청 정보 (현재 페이지, 페이지 크기 등)
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)totalCount;
        // 현재 페이지가 속한 10개 단위 블록의 끝 페이지 계산 (ex. 1~10, 11~20)
        int end =   (int)(Math.ceil( pageRequestDTO.getPage() / 10.0 )) *  10;
        // 해당 블록의 시작 페이지 계산
        int start = end - 9;
        // 실제 전체 페이지 수 계산
        int last =  (int)(Math.ceil((totalCount/(double)pageRequestDTO.getSize())));
        // 블록의 끝 페이지는 전체 페이지 수를 넘을 수 없음
        end =  end > last ? last: end;
        // 이전 페이지 블록이 존재하는 경우 (start가 1보다 크면)
        this.prev = start > 1;
        // 다음 페이지 블록이 존재하는 경우 (end * size < totalCount)
        this.next =  totalCount > (int)(end * pageRequestDTO.getSize());
        // 실제 페이지 번호 리스트 생성 (ex. [1,2,3,...,end])
        this.pageNumList = IntStream
                            .rangeClosed(start,end)
                            .boxed() // int -> Integer 객체 스트림으로 변환
                            .collect(Collectors.toList());
        if(prev) { // 이전 페이지로 이동 시 사용할 페이지 번호
            this.prevPage = start -1;
        }
        if(next) {   // 다음 페이지로 이동 시 사용할 페이지 번호
            this.nextPage = end + 1;
        }
        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();// 현재 페이지 번호 저장
    }
}
