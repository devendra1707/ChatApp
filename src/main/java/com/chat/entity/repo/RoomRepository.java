package com.chat.entity.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.entity.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

	// get room using room id

	Room findByRoomId(String roomId);

}
