package ifmo.oop.todo.domain;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;



public class Task extends Entity {

    final public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final String name;
    private Date dueDate;
    private final Date createDate;
    private Date editDate;


    private Task(long id, String name, Date dueDate, Date createDate, Date editDate) {

        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.editDate = editDate;
    }


    public Task updateDueDate(Date dueDate) {
        this.dueDate = dueDate;
        editDate = new Date(System.currentTimeMillis());
        return this;
    }


    public static Builder newBuiilder() {
        return new Builder();
    }


    public String getName() {
        return name;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public Date getEditDate() {
        return editDate;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public long generateId() {
        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
        return id = Math.abs(uuid.getLeastSignificantBits());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return task.name.equals(name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return id + "\t" + sdf.format(dueDate) + "\t" + name;

    }

    public static class Builder {

        private long id;
        private String name;
        private Date dueDate;
        private Date createDate;
        private Date editDate;


        private Builder() {
        }


        public Builder setId(long id) {
            this.id = id;
            return this;
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setDueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }


        public Builder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }


        public Builder setEditDate(Date editDate) {
            this.editDate = editDate;
            return this;
        }


        public Task build() {
            return new Task(id, name, dueDate, createDate, editDate);
        }

    }

}
