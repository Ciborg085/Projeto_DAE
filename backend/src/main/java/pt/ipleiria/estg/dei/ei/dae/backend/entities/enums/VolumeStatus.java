package pt.ipleiria.estg.dei.ei.dae.backend.entities.enums;

import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;

public enum VolumeStatus {
    SENT("sent"),
    ABORTED("aborted"),
    DELIVERED("delivered");

    private final String status;

    VolumeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public static VolumeStatus fromString(String status) throws IllegalArgumentException {
        for (VolumeStatus volumeStatus :  VolumeStatus.values()) {
            if (volumeStatus.status.equals(status)) {
                return volumeStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

