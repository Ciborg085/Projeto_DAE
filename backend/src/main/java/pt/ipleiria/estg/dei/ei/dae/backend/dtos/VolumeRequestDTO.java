package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;
import java.util.List;

public class VolumeRequestDTO implements Serializable {
    private String typeOfBox;
    private OrderForVolumeDTO order;
    private List<SensorForVolumeDTO> sensors;
}
