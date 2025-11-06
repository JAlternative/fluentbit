package fluentbit.controller;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class FluentBitController {

    @GetMapping("/user-audit")
    public String userAudit() {
        String requestId = UUID.randomUUID().toString();

        Map<String, Object> fields = Map.of(
                "category", "USER_AUDIT",
                "request_id", requestId,
                "user_login", "test",
                "user_id", "00000000-0000-0000-0000-000000000001",
                "user_fio", "Testov",
                "aomn_id", "1",
                "aomn_shortname", "Qwerty",
                "document", "ПСП и привязка ТПС – ПСП.ПСП – физические",
                "operation", "Редактирование записей",
                "@timestamp", Instant.now().toString()
        );

        log.info("Обновить ПСП по ID", StructuredArguments.entries(fields));
        return requestId;
    }

    @GetMapping("/sys-audit")
    public String sysAudit() {
        String requestId = UUID.randomUUID().toString();

        Map<String, Object> fields = Map.of(
                "category", "REST_API_AUDIT",
                "request_id", requestId,
                "class", "demo.TestLogController",
                "@timestamp", Instant.now().toString()
        );

        log.info("Системное событие тест", StructuredArguments.entries(fields));
        return requestId;
    }

    @GetMapping("/container-log")
    public String containerLog() {
        String requestId = UUID.randomUUID().toString();

        Map<String, Object> k8s = new LinkedHashMap<>();
        k8s.put("kubernetes.annotations.app_component", "evoera-reportservice");
        k8s.put("kubernetes.annotations.app_project", "ep-dev-evo-era");
        k8s.put("kubernetes.annotations.app_version", "latest");
        k8s.put("kubernetes.annotations.cattle_io/timestamp", "Mar 6, 2025 @ 13:19:10.000");

        k8s.put("kubernetes.container_hash", "erabasis-docker.artifacts.tn.tngrp.ru/reportservice-reportmanager@sha256:...");
        k8s.put("kubernetes.container_image", "erabasis-docker.artifacts.tn.tngrp.ru/reportservice-reportmanager:1.0.4-369-b4698644");
        k8s.put("kubernetes.container_name", "evoera-reportservice-reportmanager");
        k8s.put("kubernetes.docker_id", "949c4734b40bdc2a9c3f9ddeea917dela3104e26c...");
        k8s.put("kubernetes.host", "vdc01-dtnkw-02.dc-develop.tn.corp");

        k8s.put("kubernetes.labels.app", "evoera-reportservice-reportmanager");
        k8s.put("kubernetes.labels.app_root_project", "ep-dev-evo-era");
        k8s.put("kubernetes.labels.pod-template-hash", "6b4c5949b6");

        k8s.put("kubernetes.namespace_name", "ep-dev-evo-era");
        k8s.put("kubernetes.pod_id", "8a8c6831-5b2a-4e4e-92f6-affa6b18cc33");
        k8s.put("kubernetes.pod_name", "evoera-reportservice-reportmanager-6b4c5949b6-5dsvg");

        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("application_name", "reportservice-manager");
        extra.put("application_version", "1.0.4");
        extra.put("stream", "stdout");
        extra.put("time", OffsetDateTime.now().toString());
        extra.put("request_id", requestId);
        extra.put("message", "housekeeper tick");
        extra.put("log", "[2025-11-06 20:39:38,074] DEBUG reportservice.manager housekeeper");

        Map<String, Object> out = new LinkedHashMap<>(k8s);
        out.putAll(extra);
        out.put("@timestamp", Instant.now().toString());

        log.info("CONTAINER_LOG Message Test", StructuredArguments.entries(out));
        return requestId;
    }

}
