using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_GestionTareas03._01
{
    public enum EstadoTarea
    {
        Pendiente = 0,
        En_Progreso = 1,
        Terminada = 2,
        Archivada = 3
    }

    public class Tarea : INotifyPropertyChanged
    {
        private int id;
        private string titulo = string.Empty;
        private string? descripcion;
        private int color = 0;
        private DateTime vencimiento;
        private bool completado = false;
        private float porcentajeCompletado = 0.0f;
        private EstadoTarea estado;
        private DateTime fechaCreacion = DateTime.UtcNow;

        [Key]
        public int Id
        {
            get { return this.id; }
            set
            {
                this.id = value;
                OnPropertyChanged();
            }
        }

        [Required]
        [MaxLength(255)]
        public string Titulo
        {
            get { return this.titulo; }
            set
            {
                this.titulo = value;
                OnPropertyChanged();
            }
        }

        [MaxLength(2000)]
        public string? Descripcion
        {
            get { return this.descripcion; }
            set
            {
                this.descripcion = value;
                OnPropertyChanged();
            }
        }

        [Range(0, 4)]
        public int Color
        { //0 = sin color
            get { return this.color; }
            set
            {
                this.color = value;
                OnPropertyChanged();
            }
        }

        [Required]
        public DateTime Vencimiento
        {
            get { return this.vencimiento; }
            set
            {
                this.vencimiento = value;
                OnPropertyChanged();
            }
        }

        public bool Completado
        {
            get { return this.completado; }
            set
            {
                this.completado = value;
                OnPropertyChanged();
            }
        }

        [Range(0, 100)]
        public float PorcentajeCompletado
        {
            get { return this.porcentajeCompletado; }
            set
            {
                this.porcentajeCompletado = value;
                OnPropertyChanged();
            }
        }

        public EstadoTarea Estado
        {
            get { return this.estado; }
            set
            {
                this.estado = value;
                OnPropertyChanged();
            }
        }

        public DateTime FechaCreacion
        {
            get { return this.fechaCreacion; }
            set
            {
                this.fechaCreacion = value;
                OnPropertyChanged();
            }
        }


        // Relaciones entre tablas

        // FK desde Miembros(Usuarios) (1:N) //Tarea tendra un usuario asociado, no hay tareas sin usuarios
        public int UserId { get; set; }
        [ForeignKey("UserId")]
        public virtual User User { get; set; } = null!;

        // FK desde Etiquetas (1:N) //Una tarea tendra una etiqueta
        public int EtiquetaId { get; set; }
        [ForeignKey("EtiquetaId")]
        public virtual Etiqueta Etiqueta { get; set; } = null!;



        // Implementación de la interfaz INotifyPropertyChanged

        public event PropertyChangedEventHandler? PropertyChanged;

        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }

}
