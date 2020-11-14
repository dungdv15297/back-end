package poly.com.service.dto;

public enum TypeOfRoom {
    MOTEL_ROOM(0),
    APARTMENMT(1),
    HOUSE(2),
    OFFICE(3);

    TypeOfRoom(int i) {
        this.value = i;
    }

    private Integer value;


}
