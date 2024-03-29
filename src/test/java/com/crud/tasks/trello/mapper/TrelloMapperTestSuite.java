package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("id1", "List1", false));
        trelloBoardDto.add(new TrelloBoardDto("id1", "Board1", trelloListDto));
        //When
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardDto);
        //Then
        Assertions.assertEquals("Board1", trelloBoard.get(0).getName());
        Assertions.assertEquals(1, trelloBoard.get(0).getLists().size());
    }

    @Test
    void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoard = new ArrayList<>();
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("id1", "List1", false));
        trelloBoard.add(new TrelloBoard("id1", "Board1", trelloList));
        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoard);
        //Then
        Assertions.assertEquals("Board1", trelloBoardDto.get(0).getName());
        Assertions.assertEquals(1, trelloBoardDto.get(0).getLists().size());
    }

    @Test
    void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("id1", "List1", false));
        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDto);
        //Then
        Assertions.assertEquals("List1", trelloList.get(0).getName());
        Assertions.assertEquals(1, trelloList.size());
    }

    @Test
    void testMapToListDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("id1", "List1", false));
        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);
        //Then
        Assertions.assertEquals("List1", trelloListDto.get(0).getName());
        Assertions.assertEquals(1, trelloList.size());
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card1", "New card1", "pos1", "List1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assertions.assertEquals("Card1", trelloCardDto.getName());
        Assertions.assertEquals("New card1", trelloCardDto.getDescription());
        Assertions. assertEquals("pos1", trelloCardDto.getPos());
        Assertions.assertEquals("List1", trelloCardDto.getListId());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "New card1", "pos1", "List1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assertions.assertEquals("Card1", trelloCard.getName());
        Assertions. assertEquals("New card1", trelloCard.getDescription());
        Assertions.assertEquals("pos1", trelloCard.getPos());
        Assertions. assertEquals("List1", trelloCard.getListId());
    }
}