package nl.tudelft.sem.template.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.shared.domain.Node;
import nl.tudelft.sem.template.shared.domain.Schedule;
import nl.tudelft.sem.template.shared.domain.TimeSlot;
import nl.tudelft.sem.template.shared.enums.Day;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

class ScheduleTest {
    @Test
    void constructor() {
        Schedule s = new Schedule();
        assertEquals(s.getAddedSlots(), new ArrayList<>());
        assertEquals(s.getRemovedSlots(), new ArrayList<>());
        assertEquals(s.getRecurringSlots(), new ArrayList<>());
    }

    @Test
    void addRecurringSlot() {
        Schedule s = new Schedule();
        s.addRecurringSlot(new TimeSlot(1, Day.MONDAY, new Node(1, 2)));
        List<TimeSlot> p = new ArrayList<>();

        p.add(new TimeSlot(1, Day.MONDAY, new Node(1, 2)));
        assertEquals(s.getRecurringSlots(), p);
    }

    @Test
    void removeRecurringSlot() {
        Schedule s = new Schedule();
        s.addRecurringSlot(new TimeSlot(1, Day.MONDAY, new Node(1, 2)));
        List<TimeSlot> p = new ArrayList<>();

        s.removeRecurringSlot(new TimeSlot(1, Day.MONDAY, new Node(1, 2)));
        assertEquals(s.getRecurringSlots(), p);
    }

    @Test
    void removeSlot() {
        Schedule s = new Schedule();
        TimeSlot t = new TimeSlot(1, Day.WEDNESDAY, new Node(1, 2));
        s.removeSlot(t);
        List<TimeSlot> p = new ArrayList<>();

        assertEquals(s.getRemovedSlots(), p);
    }

    @Test
    void removeSlot2() {
        Schedule s = new Schedule();
        TimeSlot t = new TimeSlot(1, Day.WEDNESDAY, new Node(1, 2));
        s.addRecurringSlot(new TimeSlot(1, Day.WEDNESDAY, new Node(1, 2)));
        s.removeSlot(t);
        List<TimeSlot> p = new ArrayList<>();
        p.add(t);

        assertEquals(s.getRemovedSlots(), p);
    }

    @Test
    void addSlot() {
        Schedule s = new Schedule();
        TimeSlot t = new TimeSlot(1, Day.WEDNESDAY, new Node(1, 2));
        s.addSlot(t);
        List<TimeSlot> p = new ArrayList<>();
        p.add(t);

        assertEquals(s.getAddedSlots(), p);
    }

    @Test
    void cleanSlots() {
        Schedule s = new Schedule();
        TimeSlot t = new TimeSlot(1, Day.FRIDAY, new Node(1, 2));
        s.addSlot(t);
        s.cleanSlots(2);

        assertEquals(s.getAddedSlots(), new ArrayList<TimeSlot>());
    }
}