package org.blog.blogging.exceptions;

import lombok.Getter;
import lombok.Setter;

import static java.lang.String.*;

@Setter
@Getter
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
