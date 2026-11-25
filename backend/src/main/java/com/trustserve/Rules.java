package com.trustserve;
import java.time.*;import java.util.*;
public class Rules {
public static final List<String> SLOTS=List.of("09:00","11:00","14:00","16:00");
public static void future(LocalDate d,String slot){if(d==null||!SLOTS.contains(slot)||!LocalDateTime.of(d,LocalTime.parse(slot)).isAfter(LocalDateTime.now()))throw new IllegalArgumentException("Choose a future date and available time slot.");}
public static void transition(String old,String next){Map<String,List<String>> allowed=Map.of("Pending",List.of("Confirmed","Professional Assigned","Accepted","Rejected","Cancelled"),"Confirmed",List.of("Professional Assigned","Accepted","Cancelled"),"Professional Assigned",List.of("Accepted","Rejected","Cancelled"),"Accepted",List.of("In Progress","Cancelled"),"In Progress",List.of("Completed"));if(!allowed.getOrDefault(old,List.of()).contains(next))throw new IllegalArgumentException("Invalid status transition: "+old+" to "+next);}
public static boolean blocking(String s){return !List.of("Cancelled","Rejected").contains(s);}
public static void password(String p){if(p==null||p.length()<10||p.length()>72||!p.matches(".*[0-9].*")||!p.matches(".*[A-Za-z].*"))throw new IllegalArgumentException("Password must contain letters and digits and be 10–72 characters.");}
}