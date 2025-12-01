using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_GestionTareas03._01
{


    public enum EstadoTarea
    {
        Pendiente = 0,
        EnProgreso = 1,
        Terminada = 2,
        Archivada = 3
    }

    public class Tarea
    {
        // Atributos principales
        public int Id { get; set; }
        public string Titulo { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public string Etiqueta { get; set; } = string.Empty; 
        public string Color { get; set; } = string.Empty;
        public DateTime? Vencimiento { get; set; }
        public bool Completado { get; set; }
        public int PorcentajeCompletado { get; set; }
        public EstadoTarea Estado { get; set; }
        public DateTime FechaCreacion { get; set; }

        
        // Relación muchos a muchos: Una tarea puede tener varios usuarios
        public ICollection<User> Miembro { get; set; } = new List<User>();
    }

}
