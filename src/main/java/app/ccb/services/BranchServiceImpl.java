package app.ccb.services;

import app.ccb.config.common.constants.Constants;
import app.ccb.config.common.parsers.GsonParser;
import app.ccb.config.common.util.FileUtil;
import app.ccb.config.common.util.ValidationUtil;
import app.ccb.domain.dtos.BranchImportDTO;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final GsonParser gsonParser;
    private final ModelMapper mapper;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validator,
                             GsonParser gsonParser, ModelMapper mapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.gsonParser = gsonParser;
        this.mapper = mapper;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        String branchesFromJson = fileUtil.readFile(Constants.BRANCHES_JSON_PATH);
        return branchesFromJson;
    }

    @Override
    public String importBranches(String branchesJson) throws IOException {
        StringBuilder importResult = new StringBuilder();
        BranchImportDTO[] branchImportDTOS = gsonParser
                .fromString(this.readBranchesJsonFile(), BranchImportDTO[].class);
        for (BranchImportDTO branchImportDTO : branchImportDTOS) {
            if (!validator.isValid(branchImportDTO)) {
                importResult.append("Error: Incorrect Data!");
                importResult.append(System.lineSeparator());
                continue;
            }
            Branch branch = mapper.map(branchImportDTO, Branch.class);
            if (this.branchRepository.findByName(branchImportDTO.getName()).isPresent()) {
                continue;
            }

            this.branchRepository.saveAndFlush(branch);

            importResult.append(String.format("Successfully imported %s – %s.",
                    branch.getClass().getSimpleName(), branch.getName()));
            importResult.append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
