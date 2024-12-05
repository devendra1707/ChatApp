package com.chat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chat.entity.Message;
import com.chat.entity.Room;
import com.chat.entity.repo.RoomRepository;
import com.chat.payload.MessageRequest;

@Controller
@RequestMapping
@CrossOrigin("http://localhost:3000")
public class ChatController {

	@Autowired
	private RoomRepository roomRepository;

	// for sending and receiving messages

	@MessageMapping("/sendMessage/{roomId}")  // app/sendMessage/roomId
	@SendTo("/topic/room/{roomId}")  // subscribe
	public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {

		Room room = roomRepository.findByRoomId(request.getRoomId());

		Message message = new Message();
		message.setContent(request.getContent());
		message.setSender(request.getSender());
		message.setTimeStamp(LocalDateTime.now());

		if (room != null) {
			room.getMessage().add(message);
			roomRepository.save(room);
		} else {
			throw new RuntimeException("Room not found !!!");
		}

		return message;

	}

}
