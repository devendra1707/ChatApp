package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.Message;
import com.chat.entity.Room;
import com.chat.entity.repo.RoomRepository;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:3000")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	// create Room

	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody String roomId) {

		if (roomRepository.findByRoomId(roomId) != null) {

			// room is alread there
			return ResponseEntity.badRequest().body("Room already exist !!!");
		}

		// create new room
		Room room = new Room();
		room.setRoomId(roomId);
		roomRepository.save(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(room);
	}

	// get Room

	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRooms(@PathVariable("roomId") String roomId) {
		Room room = roomRepository.findByRoomId(roomId);
		if (room == null)
			return ResponseEntity.badRequest().body("Room not found !!!");
		return ResponseEntity.ok(room);
	}

	// get messages of room

	@GetMapping("/{roomId}/{messages}")
	public ResponseEntity<List<Message>> getMessage(@PathVariable("roomId") String roomId,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size

	) {

		Room room = roomRepository.findByRoomId(roomId);
		if (room == null) {
			return ResponseEntity.badRequest().build();
		}
		// get messages:
		// pagination

		List<Message> messages = room.getMessage();

		int start = Math.max(0, messages.size() - (page + 1) * size);
		int end = Math.min(messages.size(), start + size);
		List<Message> paginatedMessages = messages.subList(end, end);

		return ResponseEntity.ok(paginatedMessages);
	}

}
