package org.blog.blogging.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class ApiResponse {
   private String message;
   private boolean success;
}
