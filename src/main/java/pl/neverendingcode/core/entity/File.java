package pl.neverendingcode.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_sequence")
    @SequenceGenerator(name = "file_sequence", sequenceName = "file_sequence", allocationSize = 1)
    private Long id;

    @NotBlank(message = "File name must not be empty")
    @Column(name = "file_name")
    private String fileName;

    @Lob
    @JsonIgnore
    @NotNull(message = "File data must not be empty")
    @Column(name = "file_data")
    private byte[] fileData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id.equals(file.id) && fileName.equals(file.fileName) && Arrays.equals(fileData, file.fileData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileName);
        result = 31 * result + Arrays.hashCode(fileData);
        return result;
    }
}

