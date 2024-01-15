package com.springboot.hostelmanagement.service;

import com.springboot.hostelmanagement.model.Hostel;
import com.springboot.hostelmanagement.model.Room;
import com.springboot.hostelmanagement.repository.HostelRepository;
import com.springboot.hostelmanagement.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HostelService {
    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;
    public HostelService(HostelRepository hostelRepository, RoomRepository roomRepository) {
        this.hostelRepository = hostelRepository;
        this.roomRepository = roomRepository;
    }
    public List<Hostel> getAllHostel(){
        return hostelRepository.findAll();
    }
    public Optional<Hostel> getHostel(Integer id){
        return hostelRepository.findById(id);
    }
    public Hostel createHostel(Hostel hostel){
        List<Room> roomList = savedRoomsWithRepo(hostel.getRooms());
        hostel.setRooms(roomList);

        return hostelRepository.save(hostel);
    }

    private List<Room> savedRooms(List<Room> rooms) {
        return rooms.stream().map(room -> {
            room.setRoomName(room.getRoomName().toUpperCase() + " Room");
            return roomRepository.save(room);
        }).toList();
    }

    private List<Room> savedRoomsForEachStream(List<Room> rooms) {
        ArrayList<Room> returnedRooms = new ArrayList<>();
        rooms.forEach(room -> {
            room.setRoomName(room.getRoomName().toUpperCase() + " Room");
            Room save = roomRepository.save(room);
            returnedRooms.add(save);
        });
        return returnedRooms;
    }

    private List<Room> savedRoomsForLoop(List<Room> rooms) {
        ArrayList<Room> returnedRooms = new ArrayList<>();
        for (Room room : rooms) {
            room.setRoomName(room.getRoomName().toUpperCase() + " Room");
            Room save = roomRepository.save(room);
            returnedRooms.add(save);
        }
        return returnedRooms;
    }

    private List<Room> savedRoomsWithRepo(List<Room> rooms) {

        return roomRepository.saveAll(rooms);
    }

    public Optional<Hostel> updateHostel(Hostel hostel){
       hostelRepository.findById(hostel.getId());
       if(hostel.getId() == null){
           throw new IllegalArgumentException("Hostel id cannot be null");
       }
        List<Room> roomList = savedRoomsWithRepo(hostel.getRooms());
        hostel.setRooms(roomList);
        return Optional.of(hostelRepository.save(hostel));
    }
//    public Hostel updateHostel(Hostel hostel){
//        List<Room> roomList = savedRoomsWithRepo(hostel.getRooms());
//        hostel.setRooms(roomList);
//        return hostelRepository.save(hostel);
//  }
    public void deleteHostel(Integer id){
         hostelRepository.deleteById(id);
    }
}
