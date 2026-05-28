### 1.使用 Claude Code 生成实体类 2025-05-28

```
为我的 Spring Boot 项目生成 JPA 实体类，放在 com.smartqa.entity 包下。
要求使用 Lombok（@Data, @NoArgsConstructor, @AllArgsConstructor），包含必要的 JPA 注解（@Entity, @Table, @Id, @GeneratedValue, @ManyToOne 等）。
四个实体：
1. User: id(Long), username(String, 不为空), password(String, 不为空), email(String), role(String, 默认 "USER"), createdAt(LocalDateTime), updatedAt(LocalDateTime)
2. Document: id(Long), title(String), filePath(String), content(Text类型, @Lob), fileType(String), status(String, 默认 "PROCESSING"), uploadedBy(User, 多对一), createdAt(LocalDateTime)
3. Question: id(Long), title(String), content(Text), askedBy(User, 多对一), createdAt(LocalDateTime)
4. Answer: id(Long), content(Text), question(Question, 多对一), answeredBy(User, 多对一), createdAt(LocalDateTime)
请直接输出完整的 Java 代码。
```

### 2. **修改 DocumentService，异步调用解析器**

```
修改 DocumentService，在上传完成后异步调用 DocumentParser 解析文件内容，更新 Document 的 content 字段和状态（DONE/FAILED）。
使用 @Async 和自定义线程池（稍后配置），先确保能跑通。
```

