package com.seasolutions.stock_management.repository.implementations;

import com.seasolutions.stock_management.model.entity.FileUpload;
import com.seasolutions.stock_management.repository.base.BaseRepository;
import com.seasolutions.stock_management.repository.interfaces.IFileUploadRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository
public class FileUploadRepository extends BaseRepository<FileUpload> implements IFileUploadRepository {
}
