package com.example.commonthings.model;


import com.example.commonthings.entity.TypeCall;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CdrDto {
   String phoneNumber;
   LocalDateTime startTime;
   LocalDateTime endTime;
   TypeCall typeCall;

//   TODO генерация typeCall и других сущностей хромает
   public CdrDto(String phoneNumber, LocalDateTime startTime, LocalDateTime endTime, TypeCall typeCall) {
      this.phoneNumber = phoneNumber;
      this.startTime = startTime;
      this.endTime = endTime;
      this.typeCall = typeCall;

   }

}
