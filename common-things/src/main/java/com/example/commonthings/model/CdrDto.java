package com.example.commonthings.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CdrDto {
   String phoneNumber;
   LocalDateTime startTime;
   LocalDateTime endTime;
   String typeCall;

   public CdrDto(String phoneNumber, LocalDateTime startTime, LocalDateTime endTime, String typeCall) {
      this.phoneNumber = phoneNumber;
      this.startTime = startTime;
      this.endTime = endTime;
      this.typeCall = typeCall;

   }

}
