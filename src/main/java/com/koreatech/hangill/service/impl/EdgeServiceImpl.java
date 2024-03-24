package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.repository.EdgeRepository;
import com.koreatech.hangill.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EdgeServiceImpl {
    private final EdgeRepository edgeRepository;
    private final NodeRepository nodeRepository;
}
