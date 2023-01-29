package org.ahmed.init.utility;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
	private String errorMessage;
	private Integer errorCode;
	private LocalDateTime timestampInfo;
}
