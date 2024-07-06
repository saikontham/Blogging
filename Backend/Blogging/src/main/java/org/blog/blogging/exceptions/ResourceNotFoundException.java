package org.blog.blogging.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
