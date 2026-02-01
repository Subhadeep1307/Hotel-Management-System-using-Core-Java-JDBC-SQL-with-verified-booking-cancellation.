package service;


import dao.RoomDAO;
import model.Room;

import java.util.List;

public class RoomService {

    private RoomDAO roomDAO = new RoomDAO();

    public void addRoom(Room room) {
        roomDAO.addRoom(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    public List<Room> getAvailableRooms() {
        return roomDAO.getAvailableRooms();
    }

    public void updateRoomStatus(int roomId, String status) {
        roomDAO.updateRoomStatus(roomId, status);
    }
}

