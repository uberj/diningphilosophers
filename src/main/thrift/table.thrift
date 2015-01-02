namespace java com.uberj.diningphilosophers

struct Fork {
    1: i32 tablePosition
}

exception TableException {
    1: string message
}

service TTableService {
    bool pickUpFork(1: Fork fork) throws (1:TableException e),
    bool setDownFork(1: Fork fork) throws (1:TableException e),
    bool foo();
}