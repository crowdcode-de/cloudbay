package io.crowdcode.cloudbay.common;

import java.io.Serializable;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface Identifiable<ID extends Serializable> {

    ID getId();

    <T extends Identifiable<ID>> T setId(ID id);

}