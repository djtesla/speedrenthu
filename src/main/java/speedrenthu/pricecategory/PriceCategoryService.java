package speedrenthu.pricecategory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import speedrenthu.machine.Machine;
import speedrenthu.machine.MachineDto;
import speedrenthu.machine.MachineRepository;
import speedrenthu.machine.MachineService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceCategoryService {

    private MachineService machineService;

    private PriceCategoryRepository priceCategoryRepository;

    private ModelMapper modelMapper;

    @Transactional
    public PriceCategoryDto createPriceCategory(CreatePriceCategoryCommand command) {
        String name = command.getName();
        MachineDto machineDto = machineService.findMachineByName(name);
        Machine machine = modelMapper.map(machineDto, Machine.class);
        PriceCategory priceCategory = new PriceCategory(command.getDuration(), command.getAmount());
        machine.addPriceCategory(priceCategory);
        priceCategoryRepository.save(priceCategory);
        return modelMapper.map(priceCategory, PriceCategoryDto.class);
    }

    public List<PriceCategoryDto> listPriceCategories() {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAll();
        return priceCategories.stream().map(priceCategory -> modelMapper.map(priceCategory, PriceCategoryDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public PriceCategoryDto updatePriceCategoryWithAmount(long id, UpdateAmountCommand command) {
        PriceCategory priceCategory = priceCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("priceCategory cannot be find be id " + id));
        priceCategory.setAmount(command.getAmount());
        return modelMapper.map(priceCategory, PriceCategoryDto.class);
    }

    @Transactional
    public void deletePriceCategoryById(long id) {
        PriceCategory priceCategory = priceCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("priceCategory cannot be find be id " + id));
        priceCategoryRepository.delete(priceCategory);
    }


    public PriceCategoryDto findPriceCategoryByMachineAndDuration(Machine machine, PriceCategory.Duration duration) {
         PriceCategory priceCategory = priceCategoryRepository.findPriceCategoryByMachineAndDuration(machine,duration).orElseThrow(() -> new EntityNotFoundException("priceCategory cannot be find by machine and duration " + machine + ", " +duration));
    return modelMapper.map(priceCategory, PriceCategoryDto.class);
    }

    public PriceCategoryDto findPriceCategoryById(long id) {
        PriceCategory priceCategory = priceCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("priceCategory cannot be find be id " + id));
        return modelMapper.map(priceCategory, PriceCategoryDto.class);
    }
}
