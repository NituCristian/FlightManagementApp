package de.msg.flight.mapper.company;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.persistence.model.Company;

@Mapper
public interface CompanyMapper {

	CompanyMapper MAPPER = Mappers.getMapper(CompanyMapper.class);

	Company mapCompanyDtoToCompany(CompanyDto companyDto);

	CompanyDto mapCompanyToCompanyDto(Company company);
}
