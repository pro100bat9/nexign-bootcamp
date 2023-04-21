package com.example.commonthings.model;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CdrDto {
   String phoneNumber;
   LocalDateTime startTime;
   LocalDateTime endTime;
   TypeCall typeCall;

   public CdrDto(String phoneNumber, LocalDateTime startTime, LocalDateTime endTime, String typeCall) {
      this.phoneNumber = phoneNumber;
      this.startTime = startTime;
      this.endTime = endTime;
      if(typeCall.equals("01")){
         this.typeCall = TypeCall.OUTGOING;
      }
      else{
         this.typeCall = TypeCall.INCOMING;
      }
   }

}
