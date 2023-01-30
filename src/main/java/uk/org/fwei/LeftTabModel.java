package uk.org.fwei;

/**
 * Simple {@link Record} for exposing internal state information from
 * {@link LeftTabService} into the Web UI.
 * 
 * @author dpc
 */
public record LeftTabModel(int channel, int cgLayer, String state, String textSuffix) {
}
