package nl.tudelft.sem.template.shared.converters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import nl.tudelft.sem.template.shared.domain.Schedule;
import nl.tudelft.sem.template.shared.domain.TimeSlot;

@Converter
public class ScheduleConverter implements AttributeConverter<Schedule, String> {

    private static final String LIST_SPLIT_CHAR = "/";
    private static final String SLOT_SPLIT_CHAR = ";";

    private static TimeSlotConverter tsConverter = new TimeSlotConverter();

    /**
     * Converts a Schedule to a string.
     *
     * @param schedule the entity attribute value to be converted
     * @return a string representing the given schedule
     */
    @Override
    public String convertToDatabaseColumn(Schedule schedule) {
        if (schedule == null) {
            return "";
        }
        StringBuilder scheduleString = new StringBuilder();

        // For recurring slots
        timeSlotListToString(scheduleString, schedule.getRecurringSlots());
        scheduleString.append(LIST_SPLIT_CHAR);

        // For removed slots
        timeSlotListToString(scheduleString, schedule.getRemovedSlots());
        scheduleString.append(LIST_SPLIT_CHAR);

        // For added slots
        timeSlotListToString(scheduleString, schedule.getAddedSlots());

        return scheduleString.toString();
    }

    /**
     * Converts a string to a Schedule.
     *
     * @param schedule  the data from the database column to be
     *                converted
     * @return a Schedule representing the given string
     */
    @Override
    public Schedule convertToEntityAttribute(String schedule) {
        if (schedule == null || schedule.isEmpty()) {
            return new Schedule();
        }

        String[] scheduleString = schedule.split(LIST_SPLIT_CHAR, -1);
        List<TimeSlot> recurringList = stringToTimeSlotList(scheduleString[0]);
        List<TimeSlot> removedList = stringToTimeSlotList(scheduleString[1]);
        List<TimeSlot> addedList = stringToTimeSlotList(scheduleString[2]);

        return new Schedule(recurringList, removedList, addedList);
    }

    /**
     * Adds the representation of a list of slots to the given StringBuilder.
     *
     * @param scheduleString the StringBuilder
     * @param slots the list of TimeSlots to add
     */
    private void timeSlotListToString(StringBuilder scheduleString,
                                          List<TimeSlot> slots) {
        for (int i = 0; i < slots.size(); i++) {
            scheduleString.append(tsConverter.convertToDatabaseColumn(slots.get(i)));
            if (i != slots.size() - 1) {
                scheduleString.append(SLOT_SPLIT_CHAR);
            }
        }
    }

    /**
     * Converts a string to a list of TimeSlot.
     *
     * @param s the string to convert
     * @return the list of TimeSlot the string represents
     */
    private List<TimeSlot> stringToTimeSlotList(String s) {
        List<TimeSlot> slotList = new ArrayList<>();

        if (s.isEmpty()) {
            return slotList;
        }

        String[] scheduleString = s.split(SLOT_SPLIT_CHAR);

        for (String slotString : scheduleString) {
            slotList.add(tsConverter.convertToEntityAttribute(slotString));
        }
        return slotList;
    }
}
