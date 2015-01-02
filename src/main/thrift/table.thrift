namespace java com.uberj.diningphilosophers

struct Fork {
  1: i32 id
  2: i32 tablePosition
}

service TTableService {
    bool pickUpFork(1: Fork fork);
    bool setDownFork(1: Fork fork);
    bool foo();
}