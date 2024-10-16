package com.sparta.trelloproject.domain.list.service;

import com.sparta.trelloproject.common.apipayload.status.ErrorStatus;
import com.sparta.trelloproject.common.dto.AuthUser;
import com.sparta.trelloproject.common.exception.ApiException;
import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.board.repository.BoardRepository;
import com.sparta.trelloproject.domain.list.dto.request.ListSaveRequest;
import com.sparta.trelloproject.domain.list.dto.request.ListUpdateRequest;
import com.sparta.trelloproject.domain.list.dto.response.ListSaveResponse;
import com.sparta.trelloproject.domain.list.entity.List;
import com.sparta.trelloproject.domain.list.repository.ListRepository;
import com.sparta.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ListSaveResponse saveList(AuthUser authUser, Long boardId, ListSaveRequest listSaveRequest) {
        User user = User.fromAuthUser(authUser);

        Board board = findBoardById(boardId);
        List newList = listRepository.save(new List(listSaveRequest, board));

        return ListSaveResponse.of(newList);
    }

    @Transactional
    public ListSaveResponse updateList(AuthUser authUser, Long boardId, Long listId, ListUpdateRequest request) {
        User user = User.fromAuthUser(authUser);

        Board board = findBoardById(boardId);
        List list = findListById(listId);

//        List updateList = new List();
//        return ListSaveResponse.of(updateList);
        return null;
    }

    @Transactional
    public void deleteList(AuthUser authUser, Long listId) {
        User user = User.fromAuthUser(authUser);
        listRepository.deleteById(listId);
    }


    private List findListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_List));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
    }
}
